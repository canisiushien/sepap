import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DhsSectorielComponent } from './dhs-sectoriel.component';

describe('DhsSectorielComponent', () => {
  let component: DhsSectorielComponent;
  let fixture: ComponentFixture<DhsSectorielComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DhsSectorielComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DhsSectorielComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
