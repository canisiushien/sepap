import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PerfRappportComponent } from './perf-rappport.component';

describe('PerfRappportComponent', () => {
  let component: PerfRappportComponent;
  let fixture: ComponentFixture<PerfRappportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PerfRappportComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PerfRappportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
