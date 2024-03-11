import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DhsStructureComponent } from './dhs-structure.component';

describe('DhsStructureComponent', () => {
  let component: DhsStructureComponent;
  let fixture: ComponentFixture<DhsStructureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DhsStructureComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DhsStructureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
