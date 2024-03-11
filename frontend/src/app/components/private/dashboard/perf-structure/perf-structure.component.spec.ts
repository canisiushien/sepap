import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PerfStructureComponent } from './perf-structure.component';

describe('PerfStructureComponent', () => {
  let component: PerfStructureComponent;
  let fixture: ComponentFixture<PerfStructureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PerfStructureComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PerfStructureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
